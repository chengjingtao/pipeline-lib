def call(String templ, Closure after){
  node {
    after()
  }
  
  node {
    echo "hello ${templ}"
  }
}