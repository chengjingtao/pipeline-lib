def call(body){

  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()

  node{
    stage("checkout"){
      echo "this is checkout stage ${templ.Repo}"
    }
    
    if (templ["ci"] != null){
      stage("CI"){
        templ["ci"]()
      }
    }

    if (templ["buildImage"]){
      stage("Build Image"){
        echo "build image..."
      }
    }

  }
}
   