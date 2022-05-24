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

    stage('Unit Test'){
      when{
        not { branch 'main' }
      }
      steps{
        echo "unit testing"
        withMaven {
          sh 'mvn test'
        }
      }
    }

    stage('Build'){
      when{
        branch 'main'
      }
      steps{
        echo "building main"
        withMaven{
          sh 'mvn package -DskipTests'
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