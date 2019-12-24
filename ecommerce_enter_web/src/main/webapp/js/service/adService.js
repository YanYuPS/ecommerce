//服务层
app.service('adService',function($http){

    //根据分类ID查询广告列表
    this.findByCategoryId=function(categoryId){
        return $http.get("ad/findByCategoryId.do?categoryId="+categoryId);
    }

    //
    this.getName=function(){
        return $http.get("user/getName.do");
    }

});
