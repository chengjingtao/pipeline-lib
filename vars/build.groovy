def call(body){

  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()

  stage("checkout"){
    steps{
      echo "this is checkout stage ${templ.Repo}"
    }
  }
  
  stage("build"){
    steps{
      echo "this is building stage ${templ}"
    }
  }

  stage("after"){
    steps{
      script{
        templ["after"]()
      }
    }
  }
   