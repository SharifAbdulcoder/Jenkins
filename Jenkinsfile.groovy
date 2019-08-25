pipeline {
  agent any
  stages {
    stage('Run Command') {
      steps {
        sh "echo Hello, ... its mee"
      }
    }
  }
}
