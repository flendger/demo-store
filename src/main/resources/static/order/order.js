angular.module('app').controller('orderController', function ($scope, $http, $location, $routeParams) {
    const contextPath = 'http://localhost:8189/store';
    let msgTxt = "";

    $scope.fillCart = function () {
        $http.get(contextPath + "/api/v1/orders/" + $routeParams.id)
            .then(function (response) {
                $scope.order = response.data;
                $scope.order.date = new Date($scope.order.date).toLocaleDateString();
            })
    }

    $('#infoModal').on('show.bs.modal', function () {
        const modal = $(this);
        modal.find('.info-text').text(msgTxt)
    })

    $scope.fillCart();
});