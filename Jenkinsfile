pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                echo "Building for branch ${BRANCH_NAME}"
            }
        }
        stage('Deploy to Dev') {
            when {
                expression { BRANCH_NAME == 'dev' }
            }
            steps {
                echo 'deploying to dev..'
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
            when {
                expression { BRANCH_NAME == 'test' }
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
