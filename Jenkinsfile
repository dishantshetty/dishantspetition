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
                    sudo cp target/dishantspetitions-0.0.1-SNAPSHOT.jar /opt/tomcat/webapps/petition.jar

                    # Get the process IDs of the running 'petition' processes (excluding the grep process)
                    processIds=$(ps -ef | grep 'petition' | grep -v 'grep' | awk '{ print $2 }')

                    # Check if we have any valid process IDs and kill them
                    if [[ -n "$processIds" ]]; then
                      echo "Found processes: $processIds"
                      for pid in $processIds; do
                        echo "Killing process ID $pid"
                        sudo kill -9 "$pid"
                      done
                      echo "Process(es) killed."
                    else
                      echo "No valid process found."
                    fi

                    # Ensure log file permissions
                    sudo touch /opt/tomcat/webapps/log.log
                    sudo chmod 666 /opt/tomcat/webapps/log.log

                    # Start the application
                    sudo nohup java -jar /opt/tomcat/webapps/petition.jar >> /opt/tomcat/webapps/log.log 2>&1 &
                    echo "Application deployed and running."
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
