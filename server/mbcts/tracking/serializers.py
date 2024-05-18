from rest_framework import serializers
from .models import *
from useroperations.serializers import ChildrenSerializer, PruneGuardiansSerializer
from django.contrib.auth.models import User

class IncidentSerializer(serializers.ModelSerializer) :
    child_id = ChildrenSerializer(many = False, read_only = True)
    class Meta:
        model = incident
        fields = '__all__'
        
class PruneIncidentSerializer(serializers.ModelSerializer) :
    
    class Meta:
        model = incident
        fields = '__all__'
        
 
class RoutineSerializer(serializers.ModelSerializer) :
    child_id = ChildrenSerializer(many = False, read_only = True)
    class Meta:
        model = routine
        fields = '__all__'
        
class PruneRoutineSerializer(serializers.ModelSerializer) :
    
    class Meta:
        model = routine
        fields = '__all__'       
        
class Account_StatusSerializer(serializers.ModelSerializer) :
    child_id = ChildrenSerializer(many = False, read_only = True)
    parent_id = PruneGuardiansSerializer(many = False, read_only = True)
    routine = PruneRoutineSerializer(many = False, read_only = True)
    class Meta:
        model = account_status
        fields = '__all__'
        
class PruneAccount_StatusSerializer(serializers.ModelSerializer) :
    
    class Meta:
        model = account_status
        fields = '__all__'