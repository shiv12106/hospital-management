$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession
Invoke-WebRequest -Uri 'http://localhost:8081/login' -WebSession $session
Invoke-WebRequest -Uri 'http://localhost:8081/login' -Method POST -Body @{username='admin';password='admin123'} -WebSession $session
try {
    $resp = Invoke-WebRequest -Uri 'http://localhost:8081/admin/appointments' -WebSession $session
    $resp.Content
} catch {
    Write-Host "Request failed: $($_.Exception.Message)"
    $stream = $_.Exception.Response.GetResponseStream()
    $reader = New-Object System.IO.StreamReader($stream)
    $body = $reader.ReadToEnd()
    Write-Host "--- error body ---"
    Write-Host $body
}
