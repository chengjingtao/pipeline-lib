def build(body){
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  node{
    stage(config.StageName){
      sh "echo hello golang build"
    }
  }

}

def test(body){
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  sh "echo this is test"
  echo "in test echo..."
}