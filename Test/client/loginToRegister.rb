require 'rubygems'
require 'selenium-webdriver'
require 'pry'

port = 8081

driver = Selenium::WebDriver.for :firefox
driver.get "localhost:#{port}"

#Switch to Register w/o input
switch = driver.find_element(:link, "Click Here")
switch.click

if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "Passed - Switch to Register w/o input"
else
  puts "FAILED - Switch to Register w/o input"
end

#Switch back to Login w/o input
switch = driver.find_element(:link, "Click Here")
switch.click

if driver.current_url == "http://localhost:#{port}/\#/login"
  puts "Passed - Switch to Login w/o input"
else
  puts "FAILED - Switch to Login w/o input"
end

#Switch to Register with input
form = driver.find_elements(:class, "form-control")
username = form[0]
password = form[1]

username.send_keys "user1"
password.send_keys "pass1"

switch = driver.find_element(:link, "Click Here")
switch.click

if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "Passed - Switch to Register w/input"
else
  puts "FAILED - Switch to Register w/input"
end

#Switch to Login with input
form = driver.find_elements(:class, "form-control")
usernameR = form[0]
passwordR = form[1]
email = form[2]

usernameR.send_keys "user2"
passwordR.send_keys "pass2"
email.send_keys "notvalidemail"

switch = driver.find_element(:link, "Click Here")
switch.click

if driver.current_url == "http://localhost:#{port}/\#/login"
  puts "Passed - Switch to Login w/input"
else
  puts "FAILED - Switch to Login w/input"
end

driver.quit
