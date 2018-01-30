def call(body){

  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()

  stage("checkout"){
    echo "this is checkout stage ${templ.Repo}"
  }

  stage("build"){
    echo "this is building stage ${templ}"
  }

  stage("after"){
      templ["after"]()
  }
   