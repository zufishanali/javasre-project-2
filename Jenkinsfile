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

    stage('Unit Tests API 1'){
      when{
        not { branch 'main' }
      }
      steps{
        echo "unit testing API 1"
        withMaven {
          sh 'mvn -f Api1/pom.xml test'
        }
      }
    }

    stage('Unit Tests API 2'){
      when{
        not { branch 'main' }
      }
      steps{
        echo "unit testing API 2"
        withMaven {
          sh 'mvn -f restaurantApi/pom.xml test'
        }
      }
    }
    

    stage('Build'){
      when{
        branch 'main'
      }
      steps{
        echo "building main API 1"
        withMaven{
          sh 'mvn -f Api1/pom.xml package -DskipTests'
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