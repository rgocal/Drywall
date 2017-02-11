# Drywall
A wallpaper service provider that pulls wallpapers from a json file and presents them through categories in a spinner inside from the toolbar

--How To--
  Compile the project using Android Studio (Usual bells and whistles)
  Change package name to your usual domain and app name
  In the MainActivity, You will see 

//
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryOne.newInstance()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryTwo.newInstance()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryThree.newInstance()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryFour.newInstance()).commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategoryFive.newInstance()).commit();
                        break;
                    case 5:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, CategorySix.newInstance()).commit();
                        break;
//
Each case represents a different wallpaper fragment that represents a different category that is displayed in the Spinner. You may have as many as you want but try to keep all categories organized by type, color, theme, ect. If you wish to add more categories, simply copy a Category Fragment and paste it in a new fragment and create a new JSON file for the category fragment. You may host the json file on a server or here on github. Make sure the file name ends with .json. If you wish to delete a category, simply delete a case number, if you add a category, add a case number. 
  For each category fragment you have, they are named in sequentle order in arrays xml in the values folder. 
  If you use Drywall as a base project, do not delete anything regarding credits to original devs. Leave the about section alone but be sure to add a fragment for yourself. If you need assistance, ask!
  
  Basic code, basic project, very good for beginner android devs to disect and give a try. I will update the code when necessary and also push any changes any developers find necessary. 
  
  Any questions, contact me on Hangouts at rgocal09@gmail.com
  
  Drywall is based on the wallpaper services available in Paperboard
  https://github.com/jahirfiquitiva/PaperBoard-Final
