pipeline {
  /* environment {
    registry = 'ryebaird/roof'
    dockerHubCreds = 'docker_hub'
    dockerImage = '' // image id
  } */
  agent any
  stages {
    stage('Static Analysis'){
      steps{
        echo "Static code analysis"
      }
    }

    stage('Unit Tests'){
      when{
        not { branch 'main' }
      }
      steps{
        // echo "pwd"
        // dir('Api1'){
        //   sh "pwd"
        //   withMaven {
        //     sh 'mvn test'
        //   }
        //   sh "pwd"
        // }

        dir('restaurantApi'){
          echo "$WORKSPACE"
          withMaven {
            sh 'mvn test'
          }
        }
      }
    }


    stage('Build'){
      when{
        branch 'main'
      }
      steps{
        withMaven{
          sh 'mvn -f restaurantApi/pom.xml package -DskipTests'
        }
      }
    }


    stage('Docker Image'){
      when{
        branch 'main'
      }
      steps{
        echo "build docker image"
        // scripts{
        //   echo "$registry:$currentBuild.number"
        //   dockerImage = docker.build "$registry:$currentBuild.number"
        // }
      }
    }
    /*
    stage('Docker Deliver'){
      when {
        branch 'main'
      }
      steps{
        scripts{
          docker.withRegistry("",dockerHubCreds) {
            dockerImage.push("$currentBuild.number")
            dockerImgae.push("latest")
          }
        }
      }
    } */
  }
}