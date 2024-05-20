from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import views

urlpatterns = [
    path('home-tip/', views.home_tip, name = 'home-tip')
]