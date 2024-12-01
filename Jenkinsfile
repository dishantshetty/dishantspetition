pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning repository...'
                git branch: 'main', url: 'https://github.com/dishantshetty/dishantspetition.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the application...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/dishantspetitions-0.0.1-SNAPSHOT.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            steps {
                script {
                    input message: 'Deploy to server?'
                    echo 'Deploying to EC2...'
                    sh '''
                    cp target/dishantspetitions-0.0.1-SNAPSHOT.jar /opt/tomcat/webapps/petition.jar
                    java -jar /opt/tomcat/webapps/petition.jar
                    '''
                }

           }
        }
    }

    post {
        success {
            echo 'Build and deployment completed successfully!'
        }
        failure {
            echo 'Build or deployment failed!'
        }
    }
}
