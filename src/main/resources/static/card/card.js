angular.module('app').controller('cardController', function ($scope, $http, $location, $routeParams) {
    const contextPath = 'http://localhost:8189/store';
    let msgTxt = "";

    $('#infoModal').on('show.bs.modal', function () {
        const modal = $(this);
        modal.find('.info-text').text(msgTxt)
    })

    $('#myTab a').on('click', function (event) {
        event.preventDefault()
        $(this).tab('show')
    });

    $http.get(contextPath + "/api/v1/products/" + $routeParams.id)
        .then(function (response) {
            $scope.product = response.data;
        });
});