#!/bin/bash

# Hospital Management System - Railway Deployment Script

echo "🏥 Hospital Management System - Railway Deployment"
echo "=================================================="

# Check if Railway CLI is installed
if ! command -v railway &> /dev/null; then
    echo "❌ Railway CLI not found. Installing..."
    npm install -g @railway/cli
    if [ $? -ne 0 ]; then
        echo "❌ Failed to install Railway CLI. Please install Node.js first."
        exit 1
    fi
fi

echo "✅ Railway CLI found"

# Check if logged in
if ! railway whoami &> /dev/null; then
    echo "🔐 Please login to Railway:"
    railway login
fi

# Initialize Railway project
if [ ! -f "railway.json" ]; then
    echo "🚀 Initializing Railway project..."
    railway init
fi

# Build the application
echo "🔨 Building the application..."
./mvnw.cmd clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Exiting..."
    exit 1
fi

echo "✅ Build successful"

# Deploy to Railway
echo "🚀 Deploying to Railway..."
railway up

if [ $? -eq 0 ]; then
    echo "✅ Deployment successful!"
    echo ""
    echo "🌐 Your application is now live on Railway!"
    echo "📋 Next steps:"
    echo "   1. Visit Railway dashboard to get your app URL"
    echo "   2. Configure PostgreSQL database in Railway"
    echo "   3. Set environment variables for database connection"
    echo "   4. Test your application"
else
    echo "❌ Deployment failed. Please check the logs."
    exit 1
fi
