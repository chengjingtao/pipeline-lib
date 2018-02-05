def call(body){
  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()

  build {
      repo = templ["repo"]
      CI = {
          sh "date"
          echo "this is building..."
      }
      buildImage = templ["buildImage"]
  }

  deploy {
    service = "razzil"
    version = "v1"
  }

}