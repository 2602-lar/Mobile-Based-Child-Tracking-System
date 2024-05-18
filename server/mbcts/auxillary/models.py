from django.db import models
from useroperations.models import *

# Create your models here.
class safety_tip(models.Model):
    max_age = models.IntegerField()
    min_age = models.IntegerField()
    cartegory = models.CharField(max_length=255)
    tip = models.TextField()
    
class quiz(models.Model):
    max_age = models.IntegerField()
    min_age = models.IntegerField()
    question = models.TextField()
    choice_a = models.TextField()
    choice_b = models.TextField()
    choice_c = models.TextField()
    choice_d = models.TextField()
    correct = models.CharField(max_length=10)
    attempts = models.IntegerField()
    correct_attempts = models.IntegerField()
    
class gaming_stats(models.Model):
    child_id = models.ForeignKey(children, on_delete=models.CASCADE)
    max_age = models.IntegerField()
    min_age = models.IntegerField()
    game = models.CharField(max_length = 100)
    wins = models.IntegerField