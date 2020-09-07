pipeline {
  agent any
  stages {
    stage('Run Test') 
    {
      steps 
      {
        bat label: '', script: 'mvn clean install' 
      }
    }
    stage('Archive') 
          {
              archiveArtifacts 'target/*.jar'
             step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
              step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
          }
    stage('Complete') 
    {
      steps 
      {
       echo 'Success'
      }
    }
  }
}
