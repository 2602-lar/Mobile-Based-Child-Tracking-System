from django.db import models
from django.contrib.auth.models import User

#user models
class guardians (models.Model):
    user = models.ForeignKey(User , on_delete=models.CASCADE,  null=True)
    guardian_id = models.CharField(max_length = 255, unique = True, primary_key=True)
    firstname = models.CharField(max_length = 255)
    surname = models.CharField(max_length = 255)
    number_of_children = models.IntegerField()
    address = models.CharField(max_length = 255)
    phonenumber = models.CharField(max_length = 20)
    phonenumber_2 = models.CharField(max_length = 20)
    relationship = models.CharField(max_length = 255)
    
class children(models.Model):
    child_id = models.CharField(max_length = 255, unique = True, primary_key=True)
    firstname = models.CharField(max_length = 100)
    surname = models.CharField(max_length = 100)
    date_of_birth = models.DateField()
    age = models.IntegerField()
    number_of_guardians = models.IntegerField()
    
class guardian_child_relationship(models.Model):
    child_id = models.ForeignKey(children, on_delete=models.CASCADE, unique=False, null=True)
    guardian_id = models.ForeignKey(guardians, on_delete=models.CASCADE, unique=False, null=True)
