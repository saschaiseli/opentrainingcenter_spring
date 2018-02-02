pipeline {
  agent {
    docker {
      image 'maven:latest'
    }
    
  }
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean compile test -X'
      }
    }
    stage('Package') {
      steps {
        sh 'mvn package'
      }
    }
    stage('Cleanup') {
      steps {
        cleanWs(cleanWhenAborted: true, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenSuccess: true, cleanWhenUnstable: true, cleanupMatrixParent: true, deleteDirs: true)
      }
    }
  }
}