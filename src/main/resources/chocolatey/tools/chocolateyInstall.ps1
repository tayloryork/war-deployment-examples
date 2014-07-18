try {
	Write-ChocolateySuccess 'MyPackage!!!!!!!!!!'
	$version = "1.9"
	$scriptPath = (Split-Path -parent $MyInvocation.MyCommand.Definition)
	$savePath = "$scriptPath\framework-$version.zip"
	$appName = "taylorsApp"
	Write-Debug "SavePath: $savePath"
	
	Get-ChocolateyWebFile $appName $savePath "http://repo01.dev.e2open.com:8080/archiva/repository/e2open.dev/com/e2open/e2ui/framework/$version/framework-$version.zip"
    
} catch {
  Write-ChocolateyFailure $appName $($_.Exception.Message)
  throw
}