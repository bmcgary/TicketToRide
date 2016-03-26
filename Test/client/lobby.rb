#######################################################
######## This test is incomplete. Do not run ##########
#######################################################
require 'rubygems'
require 'selenium-webdriver'
require 'pry'

port = 8081

driver = Selenium::WebDriver.for :firefox
driver.get "localhost:#{port}"

#Login as a registered user
form = driver.find_elements(:class, "form-control")
username = form[0]
password = form[1]

username.send_keys "gooduser"
password.send_keys "password"

button = driver.find_element(:tag_name, "button")
button.click

puts "********** Game Lobby **********"

############### Create Game - No input #################
buttons = driver.find_elements(:tag_name, "button")
for button in buttons
    if button.text == "Create New Game"
        button.click
    end
end

create = driver.find_element(:css, "button.button.buttonGreen.ng-scope")
create.click

green = driver.find_element(:css, "button.button.buttonGreen.ng-scope")
green.click

if(driver.current_url == "http://localhost:#{port}/\#/gameLobby")
 puts "(1) Passed - Did not create a game without a name"
else
 puts "(1) FAILED - Created a game without a name"
end

############### Create Game - Too short #################
name = driver.find_element(:class, "form-control")
name.send_keys "a"

green = driver.find_element(:css, "button.button.buttonGreen.ng-scope")
green.click

if(driver.current_url == "http://localhost:#{port}/\#/gameLobby")
    puts "(2) Passed - Did not create a game too short name"
else
    puts "(2) FAILED - Created a game with too short name"
end

#This game gets created, because the input is not restrained, so you need to log out


############### Create Game - Too long ##################
name.send_keys "abcdefghijklmnopqrstuvwxyz"

green = driver.find_element(:css, "button.button.buttonGreen.ng-scope")
green.click

if(driver.current_url == "http://localhost:#{port}/\#/gameLobby")
    puts "(3) Passed - Did not create a game too long name"
else
    puts "(3) FAILED - Created a game with too long name"
end

#This game gets created, because the input is not restrained, so you need to log out



############### Create Game - Success ###################
name.send_keys "New Game!"

green = driver.find_element(:css, "button.button.buttonGreen.ng-scope")
green.click

if(driver.current_url == "http://localhost:#{port}/\#/gameLobby")
    puts "(4) Passed - Created a valid game"
else
    puts "(4) FAILED - Did not create a valid game"
end

#Logout!

############### Rejoin Game - Available #################
# Load the server state
# Find the rejoin game buttons
allGames = false;
# Loop through them and make sure the ones there match exactly, no more, no less

if allGames
    puts "(5) Passed - All Rejoin games are available"
else
    puts "(5) FAILED - Not all rejoin games are available"
end

############### Rejoin Game - Success ###################
# Select one of the rejoinable games
# Click to rejoin

if(driver.current_url == "http://localhost:#{port}/\#/game")
    puts "(6) Passed - Successfully rejoined a game"
else
    puts "(6) FAILED - Did not successfully rejoin a game"
end

#Logout

############### Join Game - List of Available ###########
# Load the server state
# Find the available game buttons
expand = driver.find_elements(:css, "i.fa.fa-plus")
expand[0].click
# Loop through available games to make sure the ones there match
allGames = false;

if(allGames)
    puts "(7) Passed - All joinable games listed"
else
    puts "(7) FAILED - Not all joinable games listed"
end

############### Join Game - No full games ###############
# Load the server state
# Find the available games button
# Loop through them to make sure none of the games have more than 4 players
notFull = false;

if(notFull)
    puts "(8) Passed - None of the joinable games are full"
else
    puts "(9) FAILED - One or more of the joinable games are full"
end
############### Join Game - Correct Info ################
# Load the server state
# Find the available games button
# Loop through them to make sure all the information is correct

############### Join Game - Success #####################
# Load the server state
# Find the joinable games
# Choose one to join and click

if(driver.current_url == "http://localhost:#{port}/\#/game")
    puts "(6) Passed - Successfully joined an existing game"
else
    puts "(6) FAILED - Did not successfully join an existing game"
end

driver.quit
