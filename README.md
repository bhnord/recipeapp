# recipeapp
wpi hacks 2022

app structure:
-------------------------------------------------------------------------------------------
welcome page

sign in page

recipes list 
	recipe selector for groceries
	favorites

individual recipe
	picture - 
	recipe name
	prep time
	serving size
	ingredients -- with quantity
	instruction
	--creator of recipe

recipe creation
	everything from indiv. recipe 

recipe find / sharing with diff account


grocery page -- just ingredients 
	ingredients
	----- local grocery stores w/ ingredients

personal pantry page
	info of what's in your pantry to subtract from grocery


database structure
--------------------------------------------------------------------------
USERS
pk - uid (username)
email
pw
--settings?

RECIPES
pk - uid w/ counter?
owner -fk
name
ingredients
prep time
instructions


SHARED RECIPES
pk - uid1/recipeid
uid1(recipe_recipient) -fk
uid2(recipe_owner) -fk
recipeid -fk








