//服务层
app.service('userService',function($http){
    //获取用户名
    this.getName=function(){
        return $http.get("user/getName.do");
    }
});
