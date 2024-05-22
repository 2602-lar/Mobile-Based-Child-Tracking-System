from django.urls import path, include
from rest_framework.routers import DefaultRouter
from . import views

urlpatterns = [
    path('live-location/', views.live_location, name = 'users'),
    path('live-location-parent', views.live_location_parent, name= 'live-location-parent'),
    path('submit-distance/',views.submit_distance, name='submit-distance'),
    path('account-stats-parent/', views.account_stats_parent, name="account-stats-parent"),
    path('acount-stats-child/', views.account_stats_child, name="account-stats-child"),
    path('child-panic/',views.child_panic, name = 'child - panic')
]
