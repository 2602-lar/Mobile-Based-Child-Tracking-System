from rest_framework import serializers
from .models import *
from django.contrib.auth.models import User

class UserSerializer(serializers.ModelSerializer) :
    class Meta:
        model=User
        # fields='__all__'
        exclude=['password']
        
class Create_UserSerializer(serializers.ModelSerializer) :
    class Meta:
        model=User
        fields='__all__'
        
class GuardiansSerializer(serializers.ModelSerializer) :
    user = UserSerializer(many = False, read_only = True)
    class Meta:
        model = guardians
        fields = '__all__'
        
class PruneGuardiansSerializer(serializers.ModelSerializer) :
    
    class Meta:
        model = guardians
        fields = '__all__'

        
class ChildrenSerializer(serializers.ModelSerializer) :
    class Meta:
        model = children
        fields = '__all__'
        



class ChildrenGuardianSerializer(serializers.ModelSerializer) :
    child_id  = ChildrenSerializer(many = False, read_only = True)
    guardian_id = GuardiansSerializer(many = False, read_only = True)
    class Meta:
        model = guardian_child_relationship
        fields = '__all__'

class Prune_ChildrenGuardianSerializer(serializers.ModelSerializer) :
    class Meta:
        model = guardian_child_relationship
        fields = '__all__'