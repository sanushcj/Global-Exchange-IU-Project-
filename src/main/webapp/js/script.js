function convertCurrency() {
    var amount = document.getElementById('amount').value;
    var fromCurrency = document.getElementById('fromCurrency').value;
    var toCurrency = document.getElementById('toCurrency').value;

    if (!amount || isNaN(amount) || amount < 0) {
        document.getElementById('result').innerHTML = 
            '<span style="color: #e53e3e;">Please enter a valid non-negative amount.</span>';
        return;
    }

    document.getElementById('result').innerHTML = 'Converting...';

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                try {
                    var response = JSON.parse(xhr.responseText);
                    if (response.success) {
                        document.getElementById('result').innerHTML = 
                            `<span style="color: #2f855a;">${amount} ${fromCurrency} = ${response.result.toFixed(2)} ${toCurrency}</span>`;
                    } else {
                        document.getElementById('result').innerHTML = 
                            `<span style="color: #e53e3e;">${response.error || 'Error converting currency. Please try again.'}</span>`;
                    }
                } catch (e) {
                    document.getElementById('result').innerHTML = 
                        '<span style="color: #e53e3e;">Error processing response. Please try again.</span>';
                    console.error('Error parsing JSON:', e, 'Response:', xhr.responseText);
                }
            } else {
                document.getElementById('result').innerHTML = 
                    '<span style="color: #e53e3e;">Server error. Please try again later.</span>';
                console.error('Server responded with status:', xhr.status, 'Response:', xhr.responseText);
            }
        }
    };

    xhr.open('GET', 'convert?from=' + fromCurrency + '&to=' + toCurrency + '&amount=' + amount, true);
    xhr.send();
}

document.getElementById('amount').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        convertCurrency();
    }
});