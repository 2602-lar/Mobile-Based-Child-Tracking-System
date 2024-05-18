# Generated by Django 5.0.3 on 2024-05-17 08:30

import django.db.models.deletion
import django.utils.timezone
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('auxillary', '0001_initial'),
        ('useroperations', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='gaming_stats',
            name='child_id',
            field=models.ForeignKey(default=django.utils.timezone.now, on_delete=django.db.models.deletion.CASCADE, to='useroperations.children'),
            preserve_default=False,
        ),
    ]