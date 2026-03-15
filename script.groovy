def buildApp() {
    echo "Building the application..."
    sh 'mvn clean package'
}

def testApp() {
    echo "Running tests..."
    sh 'mvn test'
}

def buildImage() {
    echo "Building Docker image..."

    withCredentials([usernamePassword(
        credentialsId: 'docker-hub-repo',
        passwordVariable: 'PASS',
        usernameVariable: 'USER'
    )]) {

        sh 'docker build -t nimmalabala00/demo-app:jma-3.0 .'

        sh "echo $PASS | docker login -u $USER --password-stdin"

        sh 'docker push nimmalabala00/demo-app:jma-3.0'
    }
}

def deployApp() {
    echo "Deploying application..."
}

return this