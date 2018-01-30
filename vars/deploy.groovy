def call(body){

  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()

  node {

    stage("Deploy"){
      echo "deploy service ${templ}"
    }
  }
}