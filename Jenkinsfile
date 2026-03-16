#!/usr/bin/env groovy

def gv

pipeline {

    agent any

    tools {
        maven 'maven-3.9'
    }

    parameters {
        choice(name: 'VERSION', choices: ['1.1.0','1.2.0','1.3.0'], description: 'Application version')
        booleanParam(name: 'executeTests', defaultValue: true, description: 'Run tests?')
    }

    stages {

        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }

        stage("build") {
            steps {
                script {
                    gv.buildApp()
                }
            }
        }

        stage("test") {
            when {
                expression { params.executeTests }
            }
            steps {
                script {
                    gv.testApp()
                     echo "Testing the integration"
                }
            }
        }

        stage("docker-build") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }

        stage("deploy") {
            steps {
                script {

                    def selectedEnv = input(
                        message: "Select environment",
                        parameters: [
                            choice(name: 'ENV', choices: ['dev','staging','prod'], description: '')
                        ]
                    )

                    env.ENV = selectedEnv

                    gv.deployApp()

                    echo "Deploying version ${params.VERSION} to ${env.ENV}"
                }
            }
        }
    }
}