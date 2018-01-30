def call(Map templ){
  node {
    stage("checkout"){
      echo "this is checkout stage ${templ.Repo}"
    }
    stage("build"){
      echo "this is building stage ${templ}"
    }
  }
}