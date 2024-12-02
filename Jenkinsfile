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
                    # Copy the jar file
                    sudo cp target/dishantspetitions-0.0.1-SNAPSHOT.jar /opt/tomcat/webapps/petition.jar

                    # Get the process ID of the running 'petition' process
                    processId=$(ps -ef | grep 'petition' | grep -v 'grep' | awk '{ print $2 }')

                    # Check if a valid PID exists
                    if [[ -n "$processId" && "$processId" =~ ^[0-9]+$ ]]; then
                      echo "killing $processId"
                      sudo kill -9 "$processId"
                      unset processId  # Clear the variable
                      echo "Process ID cleared."
                    else
                      echo "No valid process found or invalid process ID"
                    fi


                    sudo nohup java -jar /opt/tomcat/webapps/petition.jar >> /opt/tomcat/webapps/log.log  &
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
