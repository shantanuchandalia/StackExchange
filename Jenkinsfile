pipeline {
  agent any
  stages {
    stage('Run Test') {
      steps {
        bat label: '', script: 'mvn clean install' 
      }
    }

    stage('Complete') {
      steps {
       echo 'Success'
      }
    }

  }
}
