pipeline {
    agent any
    tools {
        maven 'my-maven'
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'mvn clean package'
                echo "Building for branch ${BRANCH_NAME}"
            }
        }
        stage('Deploy to Dev') {
            when {
                expression { BRANCH_NAME == 'dev' }
            }
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub',
                    usernameVariable: 'USERNAME',
                    passwordVariable: 'DPWD'
                )]) {
                    sh 'docker build -t thaheeroutis/jenkins:${BUILD_NUMBER} .'
                    sh "docker tag thaheeroutis/jenkins:${BUILD_NUMBER} thaheeroutis/jenkins:${BUILD_NUMBER}"
                    sh 'echo "$DPWD" | docker login -u "$USERNAME" --password-stdin'
                    sh "docker push thaheeroutis/jenkins:${BUILD_NUMBER}"
                    sh "docker run -d -p 8083:8080 thaheeroutis/jenkins:${BUILD_NUMBER}"
                }
            }

        }
        stage('Deploy to Test') {
            when {
                expression { BRANCH_NAME == 'test' }
            }
            steps {
                echo 'deploying to test..'
            }
        }
        stage('Deploy to sandbox') {
            when {
                expression { BRANCH_NAME == 'sandbox' }
            }
            steps {
                echo 'deploying to sandbox..'
            }
        }
        stage('Deploy to Prod') {
            when {
                expression { BRANCH_NAME == 'main' }
            }
            steps {
                echo 'Deploying....'
            }
        }
    }
}
