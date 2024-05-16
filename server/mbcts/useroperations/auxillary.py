import datetime
from datetime import datetime

#calculating application id 
def generate_id(model, prefix):
    year = datetime.now().year
    entity_count = model.objects.all().count() 
    place_holders = (6 - len(str(entity_count + 1))) * "0"
    entity_id = prefix + str(year) + str(place_holders) + str(entity_count + 1)
    return(entity_id)
