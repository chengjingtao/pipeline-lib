def call(body){
  def templ = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = templ
  body()

  build{
    Repo = templ["Repo"]
    after = templ["after"]
  }

  build2(templ["n"], templ["b"])
}