app.service('searchService',function($http){

    this.searchAll=function(){
        return $http.get('itemSearch/searchAll.do');
    }
    this.search=function(searchMap){
        return $http.post('itemSearch/search.do',searchMap);
    }

});