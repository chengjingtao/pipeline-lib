// step or stage
def build(body){
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def CMD = config["CMD"]

  pipeline{
    agent any

    stages{
      stage()
    }

  }
  
  node("golang"){

  }

}

def test(body){
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  sh "echo this is test ${config.Name}"
  echo "in test echo..."
}

def buildImage(body){
  pipeline{
    agent any
    stages{
      stage("Clone"){
        steps{
          echo "this is clone"
        }
      }
      stage("Build"){
        steps{
          echo "this is build"
        }
      }
      stage("Test"){
        steps{
          echo "this is test"
        }
      }
    }
  }
}