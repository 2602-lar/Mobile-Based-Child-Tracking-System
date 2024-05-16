from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import views

router = DefaultRouter()
# router.register(r'users', views.UserViewSet,basename="user")
router.register(r'guardi', views.GuardianViewSet,basename="guardi")
router.register(r'child', views.ChildrenViewSet, basename="child")
router.register(r'guardian-children', views.ChildrenGuardiansViewSet, basename="guardian-children")
router.register(r'users', views.UserViewSet, basename="user")

urlpatterns = [
    path('', include(router.urls)),
    path('users/', views.UserViewSet.users, name = 'users'),
    path('guardians/', views.creating_parent, name = 'guadians'),
    path('children/', views.creating_child, name = 'children'),
    path('create-application-user/', views.creating_application_user, name ='create-application-user')
]
