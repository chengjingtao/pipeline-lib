def call(body){

  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()
  def after = templ["after"]

  node {
    stage("checkout"){
      echo "this is checkout stage ${templ.Repo}"
    }
    stage("build"){
      echo "this is building stage ${templ}"
    }
    stage("after"){
      after.call()
    }
  }
}