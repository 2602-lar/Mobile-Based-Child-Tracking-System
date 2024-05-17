from rest_framework import serializers
from .models import *
from ..useroperations.serializers import ChildrenSerializer
        
class Safety_tipSerializer(serializers.ModelSerializer) :
    
    class Meta:
        model = safety_tip
        fields = '__all__'
    
class QuizSerializer(serializers.ModelSerializer) :
    
    class Meta:
        model = quiz
        fields = '__all__'
        
class Gaming_statsSerializer(serializers.ModelSerializer) :
    child_id = ChildrenSerializer(many = False, read_only = True)
    class Meta:
        model = gaming_stats
        fields = '__all__'
        
class PruneGaming_statsSerializer(serializers.ModelSerializer) :
    
    class Meta:
        model = gaming_stats
        fields = '__all__'