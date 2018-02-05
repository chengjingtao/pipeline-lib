def call(body){

  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()

  node{
    stage("checkout"){
      echo "this is checkout stage ${templ.repo}"
    }
    
    if (templ["CI"] != null){
      stage("CI"){
        templ["CI"]()
      }
    }

    if (templ["buildImage"]){
      stage("Build Image"){
        echo "build image..."

      }
    }
  }
  
}
   