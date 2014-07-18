$doc = [xml](Get-Content "C:\Program Files\Apache Software Foundation\Tomcat 7.0\conf\tomcat-users.xml")

$user = $doc.CreateElement('user')
$user.SetAttribute("username", "admin")
$user.SetAttribute("roles", "manager-gui,manager-script")

$tomcatUsers = $doc.SelectSingleNode("tomcat-users")
$tomcatUsers.AppendChild($user);

#$settings = New-Object System.Xml.XmlWriterSettings
#$settings.Indent = $true
#$settings.Encoding = "UTF8"

$writer = New-Object System.Xml.XmlTextWriter("C:\Program Files\Apache Software Foundation\Tomcat 7.0\conf\ok.xml", ([Text.Encoding]::Unicode))
$doc.Save($writer);