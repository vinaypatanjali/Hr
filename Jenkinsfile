pipeline
{
    agent any
    tools
    {
        maven 'maven'
        jdk 'JAVA'
    }
    options
    {
        // Append time stamp to the console output.
        timestamps()
     
        timeout(time: 1, unit: 'HOURS')
     
        // Do not automatically checkout the SCM on every stage. We stash what
        // we need to save time.
        skipDefaultCheckout()
     
        // Discard old builds after 10 days or 30 builds count.
        buildDiscarder(logRotator(daysToKeepStr: '10', numToKeepStr: '10'))
     
        //To avoid concurrent builds to avoid multiple checkouts
        disableConcurrentBuilds()
    }
    stages
    {
        stage ('checkout')
        {
            steps
            {
                checkout scm
            }
        }
        stage ('Build')
        {
            steps
            {
                //bat "mvn install"
                bat "mvn -f pom.xml -DskipTests=true install"
            }
        }
        
           stage ('Unit Testing')
        {
            steps
            {
                echo 'testing...'
            }
        }
        
        
          stage('Code Quality Check via SonarQube') {
            steps {
                withSonarQubeEnv("SonarQube")
                {
                    bat "mvn sonar:sonar"
                }
           }
       }
        
       
    }
    post
    {
        always
        {
            emailext attachmentsPattern: 'report.html', body: '${JELLY_SCRIPT,template="health"}', mimeType: 'text/html', recipientProviders: [[$class: 'RequesterRecipientProvider']], replyTo: 'vinay.patanjali@nagarro.com', subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', to: 'vinay.patanjali@nagarro.com'
        }
    }
}
