def GIT_COMMIT
def GIT_BRANCH
def BUILD_URL

pipeline {
    agent any
    options {
        timestamps()
    }
    environment {
         appName = 'CES'
         cfOrgName = 'CES'
         spaceName = 'CES'
         newmanFilePrefix = 'CESAPI'
         ENVIRONMENT_NAME = 'Prod'

        //CES_KS1_prod
        cfCredentialsIdprod = 'pcf-prod'
        cfOrgprod = '${cfOrgName}_KS1_Prod'
        spaceProd = '${spaceName}-prod'
        pcfUrlprod = 'https://api.sys.pd01.ks1.cf.ford.com'

        //CES_SH8_Preprod
        cfOrgprod2 = '${cfOrgName}_SH8_Prod'
        pcfUrlprod2 = 'https://api.sys.pd01.sh8.cf.ford.com'


	    JDK11_HOME = tool name: 'openjdk_11_0_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
	    fossa_HOME = tool name: 'fossa_3_2_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
	    JAVA_HOME = "${JAVA_HOME_ADOPTJDK11}"
	    ANT_HOME = tool name: 'cf_cli_7_2_0', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
	    JQ_HOME = tool name: 'jq_1_5', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'
        NODE_HOME = tool name: 'NodeJS_16_13_2', type: 'com.cloudbees.jenkins.plugins.customtools.CustomTool'

        PATH = "${JDK11_HOME}:${fossa_HOME}:${ANT_HOME}:${JQ_HOME}:${NODE_HOME}:${PATH}"

        HTTP_PROXY = "http://internet.ford.com:83"
        HTTPS_PROXY = "http://internet.ford.com:83"
        NO_PROXY = ".ford.com"

                projectName = 'China Enablement Systems'
        		sonarCredentialsId = 'SONARQUBE_API_KEY'
        		sonarProjectKey = 'com.ford.fc.api.ces'
        		SONAR_HOST_URL = 'https://www.sonarqube.ford.com'
        
        PROJECT_NAME = 'CES'
        GIT_URL = "https://github.ford.com/FBS-FC-API/$PROJECT_NAME"
        GIT_STATUS_URL = "https://github.ford.com/api/v3/repos/FBS-FC-API/$PROJECT_NAME/statuses"
        GIT_PULL_URL = "https://github.ford.com/api/v3/repos/FBS-FC-API/$PROJECT_NAME/pulls"

		FOSSA_SERVER = "https://ford.fossa.com"
		FOSSA_TEAM = 'CES-53399'

		BUILD_AND_SONAR_STAGE_LOG_FILE = 'build_sonar.log'
        CHECKMARX_STAGE_LOG_FILE = 'checkmarx.log'
        FOSSA_STAGE_LOG_FILE = 'fossa.log'
        DEPLOYMENT_STAGE_LOG_FILE = 'deployment.log'

        SUCCESS_STATUS = 'Succeeded'
        FAILED_STATUS = 'Failed'
    }
    stages {
	
	    stage('Pipeline Setup') {
        			steps{
        				deleteDir()
        				checkout scm
        				script {
        				    sh "chmod +x ./scripts/getCurrentDateTime.sh"
                            sh "./scripts/getCurrentDateTime.sh"
        					def scmENV = checkout scm
        					GIT_COMMIT = scmENV.GIT_COMMIT
        					GIT_BRANCH = scmENV.GIT_BRANCH
        					BUILD_URL = env.BUILD_URL
        				}
        			}
        }
        stage("PM Approval") {
        			options {
                        timeout(time: 600, unit: 'SECONDS')
                    }
        			steps {
        				script {
                            input message: 'Please click Approve for deployment approval or click Abort to stop the pipeline', ok: 'Approve',
        					submitter: "FCAPI-JENKINS-ADMINS", submitterParameter: 'Approver',
        					parameters:[
        						[$class: 'TextParameterDefinition', defaultValue: '',  name: 'Comments', description:' Enter Approval comments'],
        					]
                        }
        			}
        }

        stage('Create Environment CF homes') {
                      steps {
                         sh('ls -a $WORKSPACE')
                         sh('mkdir ' + spaceProd)
                      }
        }

       stage("Build") {
       			steps {
       				sh "echo 'shell scripts to build project...'"
       				sh "chmod +x gradlew"
       				githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "pending", "$BUILD_URL", "PR Pipeline", "Build")
       				sh './gradlew clean build --refresh-dependencies'
       			}
       			post {
       				success {
       					githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "success", "$BUILD_URL", "PR Pipeline", "Build")
       				}
       				failure {
       					githubStatus("$GIT_STATUS_URL/$GIT_COMMIT", "failure", "$BUILD_URL", "PR Pipeline", "Build")
       				}
       			}
       		}

       stage('CheckMarx Analysis') {
                steps{
                 script {
                    tee(CHECKMARX_STAGE_LOG_FILE) {
                    step([$class: 'CxScanBuilder', addGlobalCommenToBuildCommet: true, comment: '', configAsCode: true, credentialsId: '', excludeFolders: '', exclusionsSetting: 'job', failBuildOnNewResults: false, failBuildOnNewSeverity: 'HIGH', filterPattern: '''!**/_cvs/**/*, !**/.svn/**/*, !**/.hg/**/*, !**/.git/**/*, !**/.bzr/**/*,
                                    !**/.gitgnore/**/*, !**/.gradle/**/*, !**/.checkstyle/**/*, !**/.classpath/**/*, !**/bin/**/*,
                                    !**/obj/**/*, !**/backup/**/*, !**/.idea/**/*, !**/*.DS_Store, !**/*.ipr, !**/*.iws,
                                    !**/*.bak, !**/*.tmp, !**/*.aac, !**/*.aif, !**/*.iff, !**/*.m3u, !**/*.mid, !**/*.mp3,
                                    !**/*.mpa, !**/*.ra, !**/*.wav, !**/*.wma, !**/*.3g2, !**/*.3gp, !**/*.asf, !**/*.asx,
                                    !**/*.avi, !**/*.flv, !**/*.mov, !**/*.mp4, !**/*.mpg, !**/*.rm, !**/*.swf, !**/*.vob,
                                    !**/*.wmv, !**/*.bmp, !**/*.gif, !**/*.jpg, !**/*.png, !**/*.psd, !**/*.tif, !**/*.swf,
                                    !**/*.jar, !**/*.zip, !**/*.rar, !**/*.exe, !**/*.dll, !**/*.pdb, !**/*.7z, !**/*.gz,
                                    !**/*.tar.gz, !**/*.tar, !**/*.gz, !**/*.ahtm, !**/*.ahtml, !**/*.fhtml, !**/*.hdm,
                                    !**/*.hdml, !**/*.hsql, !**/*.ht, !**/*.hta, !**/*.htc, !**/*.htd, !**/*.war, !**/*.ear,
                                    !**/*.htmls, !**/*.ihtml, !**/*.mht, !**/*.mhtm, !**/*.mhtml, !**/*.ssi, !**/*.stm,
                                    !**/*.bin,!**/*.lock,!**/*.svg,!**/*.obj,
                                    !**/*.stml, !**/*.ttml, !**/*.txn, !**/*.xhtm, !**/*.xhtml, !**/*.class, !**/*.iml, !Checkmarx/Reports/*.*,
                                    !OSADependencies.json, !**/*.js, !**/node_modules/**/*''',
                                    fullScanCycle: 10,
                                    fullScansScheduled: true,
                                    generatePdfReport: true, groupId: '363',
                                    incremental: true,
                                    password: '{AQAAABAAAAAQQolJ4+odUXe/xeBqElhur4DoEsBnldxyFEPMub9JzgY=}',
                                    preset: '100005',
                                    projectName: 'CES_Prod',
                                    sastEnabled: true,
                                    serverUrl: 'https://www.checkmarx.ford.com',
                                    sourceEncoding: '1',
                                    username: '',
                                    vulnerabilityThresholdResult: 'FAILURE',
                                    waitForResultsEnabled: true])
                   	}
                 }
                 }
            post {
                      success {
                        sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                        sh "./scripts/taskLogsToSplunk.sh $CHECKMARX_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                              }
                      failure {
                         sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                         sh "./scripts/taskLogsToSplunk.sh $CHECKMARX_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                              }
                }
                }
       stage('SonarQube Analysis') {
                options {
                            timeout(time: 5, unit: 'MINUTES')
                            retry(2)
                        }
                steps {
                 script {
                          tee(BUILD_AND_SONAR_STAGE_LOG_FILE) {
                            withSonarQubeEnv('My Sonar Installation') {
                                sh "chmod +x gradlew"
                                sh './gradlew -q -PrunSonarqube=Y -Psonar.host.url=$SONAR_HOST_URL -Psonar.login=$SONAR_AUTH_TOKEN --refresh-dependencies --stacktrace --info sonarqube ' +
                                        '-Dsonar.enabled=true ' +
                                        '-Dsonar.credentialsId=SonarQube ' +
                                        '-Dsonar.projectKey=com.ford.fc.api.ces ' +
                                        '-Dsonar.hostUrl=https://www.sonarqube.ford.com ' +
                                        '-Dsonar.jacoco.reportPath=build/jacoco/jacocoTest.exec ' +
                                        '-Dsonar.language=java ' +
                                        '-Dsonar.sources=. ' +
                                        '-Dsonar.tests=. ' +
                                        '-Dsonar.test.inclusions=**/*Test*/** ' +
                                        '-Dsonar.exclusions=src/test/java/**,**/entity/**,**/config/**,**/domain/**,**/constants/**,**/exception/**,**/BankAPIApplication.*,**/blockedRoutingDetails/**,**/client/lookup/la/domain/**,**/Checkmarx/Reports/**,**/resources**' +
                                        '-Dsonar.projectVersion=$BUILD_NUMBER'
                                sh 'echo "sonarqube analysis request complete. Checking quality gate"'
                                }
                            }
                          }
                          }
                 post {
                       success {
                             sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                      sh "./scripts/taskLogsToSplunk.sh $BUILD_AND_SONAR_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                                  }
                                 failure {
                                      sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                                      sh "./scripts/taskLogsToSplunk.sh $BUILD_AND_SONAR_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                              }
                           }
                }
       stage('Test Results') {
                steps {
                        junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
                        publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'build/reports/tests/test', reportFiles: 'index.html', reportName: 'Test Results Report'])
                }
       }

       stage("FOSSA") {
                steps {
                  script {
                         tee(FOSSA_STAGE_LOG_FILE) {
                       withCredentials([string(credentialsId: "FOSSA_API_KEY", variable: "fossa_api_key")]) {
                                   sh("FOSSA_API_KEY=$fossa_api_key fossa analyze -e '$FOSSA_SERVER' -T '$FOSSA_TEAM' -p '$FOSSA_TEAM' --policy 'Website/Hosted Service Use' -b master --title '$PROJECT_NAME'")
                       }
                       }
                }
                }
                    post {
                        success {
                            sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                            sh "./scripts/taskLogsToSplunk.sh $FOSSA_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                        }
                        failure {
                            sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                            sh "./scripts/taskLogsToSplunk.sh $FOSSA_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                        }
                    }
       }
    stage('CF Map Real Route Prod (KS1)') {
          environment{
               CF_HOME = '${spaceProd}'
          }
          steps{
            script {
             tee(DEPLOYMENT_STAGE_LOG_FILE) {
             withCredentials([usernamePassword(credentialsId: cfCredentialsIdprod, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
             sh "cf --version"
             sh "cf login -a ${pcfUrlprod} -o ${cfOrgprod} -s ${spaceProd} -u $USERNAME -p $PASSWORD"
             sh "cf push ${appName} -f manifest-prod.yml --vars-file vars-ks1-prod.yml"
             }
          }
          }
          }
            post {
                success {
                    sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                    sh "./scripts/taskLogsToSplunk.sh $DEPLOYMENT_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                }
                failure {
                    sh "chmod +x ./scripts/taskLogsToSplunk.sh"
                    sh "./scripts/taskLogsToSplunk.sh $DEPLOYMENT_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
                }
            }
    }

    stage('CF Map Real Route Prod (SH8)') {
            environment{
                CF_HOME = '${spaceProd}'
            }
            steps{
               withCredentials([usernamePassword(credentialsId: cfCredentialsIdprod, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
               sh "cf --version"
               sh "cf login -a ${pcfUrlprod2} -o ${cfOrgprod2} -s ${spaceProd} -u $USERNAME -p $PASSWORD"
               sh "cf push ${appName} -f manifest-prod.yml --vars-file vars-sh8-prod.yml"
               }
            }
    }

    }
     post {
         success {
             sh "chmod +x ./scripts/taskLogsToSplunk.sh"
             sh "./scripts/taskLogsToSplunk.sh $INTEGRATION_STAGE_LOG_FILE $SUCCESS_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
         }
         failure {
             sh "chmod +x ./scripts/taskLogsToSplunk.sh"
             sh "./scripts/taskLogsToSplunk.sh $INTEGRATION_STAGE_LOG_FILE $FAILED_STATUS $PROJECT_NAME $ENVIRONMENT_NAME"
         }
     }
}
def githubStatus(String githubUrl, String state, String targetUrl, String description, String context) {
	withCredentials([string(credentialsId: "Github", variable: "githubToken")]) {
		sh "curl -X POST -H \"Authorization: token $githubToken\" $githubUrl -d \'{\"state\":\"$state\",\"target_url\": \"$targetUrl\", \"description\": \"$description\", \"context\": \"$context\"}\'"
	}
}
