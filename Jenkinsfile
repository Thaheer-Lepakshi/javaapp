pipeline {
    agent any

    stages {
        stage('Build') {
            when {
                expression { BRANCH_NAME == 'main' }
            }
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
             when {
                expression { BRANCH_NAME == 'main' }
            }
            steps {
                echo 'Deploying....'
            }
        }
    }
}
