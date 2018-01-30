def call(templ, Closure after){
  node {
    after()
  }
  
  node {
    echo "hello ${templ}"
  }
}