package com.project2.api1.Services;

import com.project2.api1.Models.*;
import com.project2.api1.Models.MenuItem;
import com.project2.api1.Repositories.CustomerRepository;
import com.project2.api1.Repositories.MenuItemRepository;
import com.project2.api1.Repositories.OrdersRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OrdersService {
    final Logger logger = LoggerFactory.getLogger(OrdersService.class);

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    public boolean deleteOrder(Orders o) {
        ordersRepository.delete(o);
        return true;
    }

    public int saveOrder(Orders o) {
        return ordersRepository.save(o).getOrderId();
    }

    public Orders getItem(int id) throws Exception {
        if (ordersRepository.findById(id).isPresent()) {
            return ordersRepository.findById(id).get();
        } else
            throw new Exception();
    }

    public List<Orders> getAllItems(int id) {

        return ordersRepository.findAllByCustomerId(id);
    }

    public boolean isInRange(int customerId) throws IOException {

       String[] addr = customerRepository.findById(customerId).get().getAddress().split(" ");

       StringBuilder str = new StringBuilder();
       for (String s : addr)
       {
           str.append(s);
           str.append("+");
       }
       String[] city = customerRepository.findById(customerId).get().getCity().split(" ");
        for (String s : city)
        {
            str.append(s);
            str.append("+");
        }
       str.append("+");
       str.append(customerRepository.findById(customerId).get().getState());



        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/directions/json?origin="+str+"&destination=1955+S+Stapley+Dr+Mesa+AZ&key=<PAST API KEY HERE>")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String news = response.body().string();
        int ind = news.indexOf("text");
        String testnew = news.substring(ind,ind+15);
        String testtest = testnew.substring(9);
        String[] fnl = testtest.split(" ");
        double distance = Double.parseDouble(fnl[0]);
        if (distance<=25) {
            return true;
        }
        return false;
    }

    public int createOrder(OrderDTO o)
    {
        List<MenuItem> lst = new ArrayList();
        for (int i : o.getItems())
        {
            lst.add(menuItemRepository.findById(i).get());
        }
        Orders order = new Orders(99,o.getCustomerId(), LocalDateTime.now(),null,"New",lst);
        return ordersRepository.save(order).getOrderId();
    }

    public String placeOrder(int oId)
    {
        Customer c = customerRepository.findById(ordersRepository.findById(oId).get().getCustomerId()).get();
        RestTemplate tmp = new RestTemplate();
        HttpEntity<TransferOrderDTO> req = new HttpEntity<>(new TransferOrderDTO(oId,c.getContactType(),c.getEmailAddress(),c.getPhoneNumber()));
        String resourceUrl = "http://localhost:8000/restaurant/getOrder";
        ResponseEntity<String> response = tmp.exchange(resourceUrl, HttpMethod.PUT,req,String.class);
        return response.getBody();
    }
}
