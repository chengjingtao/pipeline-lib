def call(body){
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def repo = config["repo"]
  def branch = config["branch"]?:"master"
  def credentialsId = config['credentialsId']

  def slave = config["slave"]
  def cmd = config["cmd"]
  def dockerfileDir = config["dockerfileDir"]?:"."
  def registry = config["registry"]?:"index.alauda.cn"
  def registryCredentialsId = config["registryCredentialsId"]
  def image = config["image"]



  def ALAUDACI_DEST_DIR = "/__alauda_dest"
  pipeline{
    agent any

    stages{
      stage("Clone"){
        steps{
          echo "clone ${repo}"

          script{
            def scmVars = git url:repo, branch:branch, credentialsId:credentialsId
            gitCommitID = scmVars.GIT_COMMIT
            gitBranch = scmVars.GIT_BRANCH
          }

          stash "source"

          echo "clone ${repo} succeed"
        }
      }

      stage("ExeCMDs"){
        agent {
          label "${slave}"
        }

        steps{
          unstash "source"
          sh "ls && mkdir -p ${ALAUDACI_DEST_DIR}"
          withEnv(["ALAUDACI_DEST_DIR=${ALAUDACI_DEST_DIR}"]){
            sh """${cmd}"""
          }

          dir("${ALAUDACI_DEST_DIR}"){
            stash "dest"
          }
        }
      }

      stage("BuildAndPushImage"){
        steps{
          sh "mkdir -p ${ALAUDACI_DEST_DIR}"
          dir("${ALAUDACI_DEST_DIR}"){
            unstash "dest"
            script{
              docker.withRegistry(registry, registryCredentialsId){
                docker.build("${image}:${gitCommitID}", "${ALAUDACI_DEST_DIR}").push()
              }
            }
          }
        }
      }

    }

  }
}

// def test(body){
//   def config = [:]
//   body.resolveStrategy = Closure.DELEGATE_FIRST
//   body.delegate = config
//   body()

//   pipeline {
//       agent any
//       stages {
//         stage('Even Stage') {
//           steps {
//             echo "The build number is even"
//           }
//         }
//       }
//     }
// }

  