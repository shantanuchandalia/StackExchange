pipeline {
  agent any
  stages {
    stage('Run Test') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Complete') {
      steps {
        sh 'echo 1'
      }
    }

  }
}